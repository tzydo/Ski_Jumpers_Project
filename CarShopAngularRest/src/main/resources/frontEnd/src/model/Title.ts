export class Title {

  private _title: string;
  private _name: string;
  private _lastName: string;
  private _email: string;

  constructor(title: string, name: string, lastName: string, email: string) {
    this._title = title;
    this._name = name;
    this._lastName = lastName;
    this._email = email;
  }

  get title(): string {
    return this._title;
  }

  set title(value: string) {
    this._title = value;
  }

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  get lastName(): string {
    return this._lastName;
  }

  set lastName(value: string) {
    this._lastName = value;
  }

  get email(): string {
    return this._email;
  }

  set email(value: string) {
    this._email = value;
  }





}
