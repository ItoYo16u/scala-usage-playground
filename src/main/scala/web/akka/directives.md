# Custom Directive
Custom Directiveを作るには、次のような方法がある.
1. 既存のDirectiveを `|` や `&` で合成する
1. 既存のDirectiveのパラメタを一部変更する, 既存のDirectiveをmapする
1. 既存のDirectiveを継承しメソッドをoverrideする

## JWTDirective ( < AuthenticateDirective[T] < Directive1[T])
- HTTP headerに`Authorization: Bearer token`の形式で付与されたJWTTokenを抽出し、正当性を検証する.
- `authenticateOAuth2Async`のauthenticatorコールバックを変更することで作成できる
  - tokenは`bearer `以降の値が抽出され、Credentials型にmapされて後続の処理に渡されるのでそれをパターンマッチして取得できる

