

module.exports = {
  entry: './src/main/resources/static/js/app.js', // エントリーポイントのパス
  output: {
    filename: 'bundle.js', // バンドルされたファイルの名前
    path: path.resolve(__dirname, 'dist'), // バンドルされたファイルの出力先ディレクトリ
  },
  resolve: {
    // Webpackに拡張子を解決させる
    extensions: ['.js', '.jsx'], // 必要に応じて他の拡張子も追加
  },
};
