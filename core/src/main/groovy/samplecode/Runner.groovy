import java.awt.*;

public class Runner
{
    def pwd = System.properties.'user.dir';
    def pwdf = new File(pwd);

    def say(tx) {println tx;}
    
    def setPWD(def name) 
    {
        if (new File(name.trim()).exists())
        {
            pwd = name.trim();
            pwdf = new File(pwd);
            return true;
        }
        else return false;
    } // end of 
    
    
    def cd(String workingDir)
    {
        return (setPWD(workingDir)) ? true : false;    
    } // end of cd
    
    def run(String command) 
    {
          return run(command, pwdf)
    }
 
    def run(String command, String workingDir) 
    {
        if (setPWD(workingDir))
        {
            return run(command, pwdf)    
        }
    } // end of run

    // intercept cd command and fix our pwd to match
    def ckCD(cmd)
    {
        def cmdtxt = cmd.trim();
        if (cmdtxt.toLowerCase().startsWith("cd "))
        {
            cmdtxt = cmdtxt.substring(3);
            def tf = cd(cmdtxt);
            return tf;
        } // end of if
        else
        {
            return false;
        } 
    } // end of ck
    
    
    def run(String command, File workingDir) 
    {
          say "\n--- running:"+command

          def tf = ckCD(command)
          def cwdf = (tf) ? pwdf : workingDir  
          def process = new ProcessBuilder(addShellPrefix(command))
                                    .directory(cwdf)
                                    .redirectErrorStream(true) 
                                    .start()
          process.inputStream.eachLine {println it}
          process.waitFor();
          return process.exitValue()
    } // end of run

 
def addShellPrefix(String command) {
  def commandArray = new String[3]
  commandArray[0] = "sh"
  commandArray[1] = "-c"
  commandArray[2] = command
  return commandArray
}

  public static void main(String[] args)
  {
    println "-- the start --"
    def r = new Runner();

    def ans = r.run("pwd");
    ans = r.run("gradlew");
    r.say "ans="+ans

    r.cd("/Volumes/Media");
    ans = r.run("pwd");
    r.say "ans="+ans

    ans = r.run(" cd  /Volumes/Media/Users");
    r.say "ans="+ans
    ans = r.run("pwd");
    r.say "ans="+ans


    ans = r.run("ls -al");
    r.say "ans="+ans


    ans = r.run("echo 'hi kids'");
    r.say "ans="+ans


    ans = r.run("java -jar wordstar-1.0.jar","/Volumes/FHD-XS/wordstar/build/libs");
    r.say "ans="+ans
    
    println "-- the end --"
  } // endof main
      
} // end of class