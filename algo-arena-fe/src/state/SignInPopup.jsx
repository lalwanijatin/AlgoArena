import { atom } from 'recoil';

export const popupState = atom({
  key: 'popupState', // unique ID (with respect to other atoms/selectors)
  default: false, // default value (aka initial value)
});