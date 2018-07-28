import entity.Student;
import javax.ws.rs.POST;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

class Repo {


    public Repo() {

    }

    public ArrayList<Student> getAllStudents()
    {
        ArrayList<Student> studentsArray = new ArrayList<>();
        try {
            org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();

            Object obj = parser.parse(new FileReader("E:\\test.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray students = (JSONArray) jsonObject.get("students");
            for (int i = 0; i < students.size(); i++) {
                JSONObject student = (JSONObject) students.get(i);
                String name = (String) student.get("name");
                String id = (String) student.get("id");
                studentsArray.add(new Student(id, name));
            }
            return studentsArray;
        }
        catch (Exception e){
            System.out.println("Reading File error");
            return null;
        }
    }

    public Student addStudent(Student student)
    {
        ArrayList<Student> studentsArray;
        try {
            studentsArray = getAllStudents();
            studentsArray.add(student);
            writeToFileJson(studentsArray);
            return student;
        }
        catch (Exception e)
        {
            System.out.println("Error parsing Json Response");
            return null;
        }

    }

    public void updateStudent(Student student)
    {
        ArrayList<Student> studentsArray;
        try {
            studentsArray = getAllStudents();
            Student oldStudent = findStudent(studentsArray, student.getId());
            int index = studentsArray.indexOf(oldStudent);
            if(oldStudent != null)
            {
                studentsArray.remove(oldStudent);
                studentsArray.add(index, student);
            }
            System.out.println(studentsArray.size());
            writeToFileJson(studentsArray);
            return;
        }
        catch (Exception e)
        {
            System.out.println("Error parsing Json Response");
            return;
        }
    }


    public void deleteStudent(String id)
    {
        ArrayList<Student> studentsArray;
        try {
            studentsArray = getAllStudents();
            Student student = findStudent(studentsArray, id);

            if(student != null)
            {
                studentsArray.remove(student);
            }
            System.out.println(studentsArray.size());
            writeToFileJson(studentsArray);
            return;
        }
        catch (Exception e)
        {
            System.out.println("Error parsing Json Response");
            return;
        }
    }
    private Student findStudent(ArrayList<Student> students,String id)
    {
        for(int i = 0; i < students.size(); i++)
        {
            if(students.get(i).getId().equals(id))
                return students.get(i);
        }
        return null;
    }

    private void writeToFileJson(ArrayList<Student> students)
    {
        try {
            JSONObject obj = new JSONObject();
            JSONArray list = new JSONArray();
            for (Student s : students) {
                JSONObject newStudent = new JSONObject();
                newStudent.put("name", s.getName());
                newStudent.put("id", s.getId());
                list.add(newStudent);
            }
            obj.put("students", list);
            FileWriter file = new FileWriter("E:\\test.json");

            file.write(obj.toJSONString());
            file.flush();
        }
        catch (Exception e)
        {

        }
    }


}
