// work01.js
import React from 'react';
import ReactDOM from 'react-dom';

// 以降のコードを続ける


// Greetingコンポーネントの定義
function Greeting(human) {
    // propsから名前を受け取り、Hello, [名前]と表示する
    return <h3>Hello, {human.name}</h3>;
  }

  ReactDOM.render(
    <div>
        <h4>work01</h4>
        <p>
        </p>
    </div>,
    document.getElementById('root')
);

export default Greeting;
