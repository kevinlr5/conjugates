var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');

var NODE_ENV = process.env.NODE_ENV;

var FILE_LOADER = 'file-loader?name=[name].[hash].[ext]'

var plugins = [
  new webpack.DefinePlugin({
    'process.env': {
      NODE_ENV: JSON.stringify(NODE_ENV),
    }
  }),
  new HtmlWebpackPlugin({
    inject: false,
    template: require('html-webpack-template'),
    title: 'Conjugates frontend',
    hash: true,
    appMountId: 'fixture'
  })
];

if (process.env.NODE_ENV === 'production') {
  console.log('** WEBPACK PRODUCTION MODE ENABLED **');
  plugins.push(new webpack.LoaderOptionsPlugin({
    minimize: true
  }));
  plugins.push(new webpack.DefinePlugin({
    '__API_URL__': JSON.stringify('@API_URL'),
  }));
  plugins.push(new webpack.optimize.UglifyJsPlugin({
    output: {
      comments: false
    },
    sourceMap: true
  }));
} else {
    console.log('** WEBPACK DEV MODE ENABLED **');
    plugins.push(new webpack.DefinePlugin({
      '__API_URL__': JSON.stringify('http://localhost:9090'),
    }));
}

module.exports = {
  entry: {
    main: './ts/main.tsx'
  },
  output: {
    path: __dirname + '/build',
    filename: '[name].js'
  },
  devtool: 'source-map',

  plugins: plugins,

  resolve: {
    extensions: ['.webpack.js', '.web.js', '.ts', '.tsx', '.js']
  },

  module: {
    rules: [
      { test: /\.tsx?$/, use: 'tslint-loader', enforce: 'pre' },
      { test: /\.js$/, use: 'source-map-loader', enforce: 'pre' },
      { test: /\.tsx?$/, use: 'ts-loader' },
      { test: /\.scss$/, use: ['style-loader', 'css-loader', 'sass-loader'] },
      { test: /\.css$/, use: ['style-loader', 'css-loader'] },
      { test: /\.png$/, use: 'file-loader' },
      { test: /\.woff2(\?v=\d+\.\d+\.\d+)?$/, use: FILE_LOADER },
      { test: /\.woff(\?v=\d+\.\d+\.\d+)?$/, use: FILE_LOADER },
      { test: /\.eot(\?v=\d+\.\d+\.\d+)?$/, use: FILE_LOADER },
      { test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/, use: FILE_LOADER },
      { test: /\.svg(\?v=\d+\.\d+\.\d+)?$/, use: FILE_LOADER }
    ]
  }
}
