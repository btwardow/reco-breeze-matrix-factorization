name := "reco-breeze-matrix-factorization"

organization := "pl.edu.pw.ii.btwardowski"

version := "0.0.1"

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
  "org.scalanlp" %% "breeze" % "0.9",
  "org.specs2" %% "specs2" % "2.4" % "test"
)

scalacOptions in Test ++= Seq("-Yrangepos")

// Read here for optional dependencies:
// http://etorreborre.github.io/specs2/guide/org.specs2.guide.Runners.html#Dependencies

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

initialCommands := "import pl.edu.pw.ii.btwardowski._"
