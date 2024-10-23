import { atom } from 'recoil';

export const languageIdState = atom({
  key: 'languageIdState', // unique ID (with respect to other atoms/selectors)
  default: localStorage.getItem("languageId") ? localStorage.getItem("languageId") : '62', // default value (aka initial value)
});