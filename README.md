## 課題要求
- 書籍管理システム
- 書籍には著者の属性があり、書籍と著者の情報をRDBに登録・変更・検索ができる
- 著者に紐づく本を取得できる

【補足】
- APIのみ（※Viewはなし）
- テストコード付

### 想定ユースケース
```puml
@startuml
left to right direction
actor "利用者" as User
rectangle 書籍管理システム {
  package 著者 {
    usecase "著者を登録する" as Author_U_1
    usecase "著者を更新する" as Author_U_2
    usecase "著者を検索する" as Author_U_3
    usecase "著者の詳細を取得する（※執筆書籍の一覧を得られる）" as Author_U_4
  }
  package 書籍 {
    usecase "書籍を登録する" as Book_U_1
    usecase "書籍を更新する" as Book_U_2
    usecase "書籍を検索する" as Book_U_3
  }
  
}

User --> Author_U_1
User --> Author_U_2
User --> Author_U_3
User --> Author_U_4

User --> Book_U_1
User --> Book_U_2
User --> Book_U_3
@enduml
```

## 前提事項

観点
> 参照透過性を意識した安全性の高いコードをKotlinで書けるかどうか

そこを外れる部分は基本的に簡素化しています。

機能設計的な部分も私個人で都合よく解釈させていただいております。

具体的には、
- バリデーションやロギング・例外ハンドリングは今回は観点の外として省略しています。
- gradleの設定周りもひとまず動くもの以上のことは目指していません。
- アーキテクチャーは仮にDDDっぽくやるならという体です。
- ライブラリを合わせてJOOQをO/Rマッパーに初めて採用してみました。
  - こちらは知見等からよりよい記述や設計がありましたらご教授（プルリクでも何でも）いただけると嬉しく思います。
    - 特に悩んだ部分
      1. 検索系の条件の部分的な共通化をどうするのが良いのか？共通化しない方がいいのか？上手く部分適用できないか？
      2. 割と記述に自由度がありすぎて統一感どう出しているのか？どうゆうルールに寄せているのか？

## 課題

- テストコンテキスト起動時にH2へのインサート処理が複数回走っている模様（現状：原因調査間に合わず・・）