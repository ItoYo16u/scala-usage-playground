sbtでmaven repositoryで管理されているJavaのライブラリを使うにはMaven groupId, artifactIdとversionをsbtのlibraryDependenciesの文字列に変換する必要がある.

sntとMavenは共にApache Ivyを内部的に使っている. また、ssbtはMaven2レポジトリをデフォルトのresolverとして使っているので以下のようにMavenのdependencyをsbtのlibraryDependenciesに書き換えることでMavenで管理されているJavaのライブラリを使える.

```
<dependency>
<groupId>com.example.example</groupId>
<artifactId>libraryName</artifactId>
<version>x.x.x</version>
</dependency>
```

上のようなdependency設定があるとき、これを以下のようにsbtのlibraryDelendenciesに書く.

```
libraryDependencies += "com.example.example" % "libaryName" % "x.x.x"
```
