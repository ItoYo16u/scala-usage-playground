# about

[このリンク](https://nrinaudo.github.io/scala-best-practices/tricky_behaviours/final_case_classes.html)
を参考にした


sealed abstract case classを使うことで、
case classのunapply()やcopyなどのメソッドを残したまま、
new キーワードによる初期化やapply()メソッドによる初期化を制限し、
コンパニオンオブジェクトの特定のメソッドからしかインスタンスを生成できないようにする