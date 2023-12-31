import * as React from "react";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { request, setAuthHeader } from "../helpers/axios_helper";

export default class Login extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      active: "login",
      surname: "",
      firstname: "",
      patronymic: "",
      email: "",
      password: "",
      showPassword: false,
    };
  }
  onChangeHandler = (event) => {
    let name = event.target.name;
    let value = event.target.value;
    this.setState({ [name]: value });
  };

  onLogin(e, email, password) {
    e.preventDefault();
    request(
      "POST",
      "/login",
      {
        email: email,
        password: password
      }).then(
        (response) => {
          setAuthHeader(response.data.token);
          window.location.reload();
        }).catch(
          (error) => {
            setAuthHeader(null);
            toast.error('Неверный логин или пароль!', {
              position: toast.POSITION.TOP_RIGHT,
              autoClose: 3000,
            });
          }
        );
  };

  onSubmitLogin = (e) => {
    e.preventDefault();
    if (this.state.password.length > 0) {
      this.onLogin(e, this.state.email, this.state.password);
    } else {
      toast.error("Введите пароль!", {
        position: toast.POSITION.TOP_RIGHT,
        autoClose: 3000,
      });
    }
  };

  togglePasswordVisibility = () => {
    this.setState((prevState) => ({
      showPassword: !prevState.showPassword,
    }));
  }

  render() {
    return (
      <div className="flex justify-center">
        <div className="w-1/4 bg-white bg-opacity-100 rounde shadow-xl p-2">
          <div className="flex justify-center mb-3">
            <h2 className="text-2xl font-bold text-gray-800">Авторизация</h2>
          </div>
          <div className="p-4 rounded">
            <form onSubmit={this.onSubmitLogin}>
              <div className="mb-4">
                <label className="block text-sm font-medium text-gray-800" htmlFor="email">
                  Электронная почта
                </label>
                <input
                  type="email"
                  id="email"
                  name="email"
                  className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2"
                  onChange={this.onChangeHandler}
                  required
                />
              </div>

              <div className="mb-4">
                <label className="block text-sm font-medium text-gray-800" htmlFor="password">
                  Пароль
                </label>
                <input
                  type={this.state.showPassword ? "text" : "password"}
                  id="password"
                  name="password"
                  className="w-full border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2"
                  onChange={this.onChangeHandler}
                  required
                />
                <button
                  type="button"
                  className="text-blue-600 underline focus:outline-none"
                  onClick={this.togglePasswordVisibility}
                >
                  {this.state.showPassword ? "Скрыть пароль" : "Показать пароль"}
                </button>
              </div>

              <button
                type="submit"
                className="w-full bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded focus:outline-none"
              >
                Войти
              </button>
            </form>
          </div>
        </div>
      </div>
    );
  }
}
