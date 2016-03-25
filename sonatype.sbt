// Your profile name of the sonatype account. The default is the same with the organization value
sonatypeProfileName := "pl.setblack"

// To sync with Maven central, you need to supply the following information:
pomExtra in Global := {
  <url>https://github.com/lightserver/cryptotpyrc</url>
    <licenses>
      <license>
        <name>The BSD 3-Clause License</name>
        <url>https://opensource.org/licenses/BSD-3-Clause</url>
      </license>
    </licenses>
    <scm>
      <connection>scm:git:github.com/lightserver/cryptotpyrc.git</connection>
      <developerConnection>scm:git:git@github.com:lightserver/cryptotpyrc.git</developerConnection>
      <url>github.com/lightserver/cryptotpyrc.git</url>
    </scm>
    <developers>
      <developer>
        <id>jarek000000</id>
        <name>Jarek Ratajski</name>
        <url>www.setblack.pl</url>
      </developer>
    </developers>
}