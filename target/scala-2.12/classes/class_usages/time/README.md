# Scalaのクラスの使い方の練習

[このリンク](https://mi12cp.hatenablog.com/entry/2018/08/09/151231)
を参考にした

また、ScalaTestを用いてTest As Specificationを書きテスト駆動で書いた

時間の処理をクラスを使って実装してみる

Time: 特定の時間をあらわす

Duration: 二つの時間A,Bの間の経過時間をあらわす

TimeRange: 始めと終わりの2つの時間の組をあわらす

Aさんが、ある時間t_0に移動を開始したとき、
目的地oへ到着するのにdかかる.

Aさんは、t_1からt_2の間に目的地oに到着しなければならない。

TimeRangeを使ってこの移動が間に合うかを判定する
