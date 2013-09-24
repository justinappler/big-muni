name := "big-muni"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-xml" % "2.1.3"
)     

play.Project.playJavaSettings
