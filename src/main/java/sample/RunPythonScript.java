package sample;
import java.io.*;



// run this way

// javac JavaRunCommand.java

// java -classpath . JavaRunCommand



public class RunPythonScript {
    private Boolean isDone =false;
    private String output="";

public String RunAndroidIconsGeneratorScripts(String iconPath,String destination){


        String st = null;



        try {



            String[]callAndArgs= {"python","./android_icons_generator.py",iconPath,destination};

            Process p = Runtime.getRuntime().exec(callAndArgs);



            BufferedReader stdInput = new BufferedReader(new

                    InputStreamReader(p.getInputStream()));



            BufferedReader stdError = new BufferedReader(new

                    InputStreamReader(p.getErrorStream()));



            // read the output
             String s;
            while ((s = stdInput.readLine()) != null) {

                System.out.println(s);
                output+=s;
            }
            isDone=true;


            // read any errors

            while ((s = stdError.readLine()) != null) {

                System.out.println(s);
                output+=s;
                isDone=false;
            }

            return output;
        }

        catch (IOException e) {

                System.out.println("exception occured");

                        e.printStackTrace();
                isDone=false;
                return "Exception Occured : "+e.getMessage();

            }

        }

    public Boolean getStatus() {
        return isDone;
    }
}



