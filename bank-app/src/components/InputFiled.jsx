import React from 'react';
import './InputFiled.scss';

const InputField = ({ type, placeholder, value, onChange }) => {
  return (
    <input
      type={type}
      placeholder={placeholder}
      value={value}
      onChange={onChange}
      className='InputField'
    />
  );
};

export default InputField;
