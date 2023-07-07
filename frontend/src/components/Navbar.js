import React, { useState } from "react";
import { NavLink } from "react-router-dom";
import { request, setAuthHeader } from "../helpers/axios_helper";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import ReactModal from "react-modal";
import Login from "./Login";


ReactModal.setAppElement("#root");

function logout() {
    setAuthHeader(null);
    window.location.reload();
    window.location.assign('/')
};

export function onLogin(e, email, password) {
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

export const Navbar = ({ isAuthenticated }) => {
    const [isModalOpen, setIsModalOpen] = useState(false);
  
    const openModal = () => {
      setIsModalOpen(true);
    };
  
    const closeModal = () => {
      setIsModalOpen(false);
    };
  
    const onLogin = (e, email, password) => {
      e.preventDefault();
      if (password.length > 0) {
        // Добавьте здесь вашу логику для авторизации
        toast.success("Успешная авторизация!");
        closeModal();
      } else {
        toast.error("Введите пароль!", {
          position: toast.POSITION.TOP_RIGHT,
          autoClose: 3000,
        });
      }
    };
  
    return (
      <>
        <nav className="bg-sky-600">
          <div className="max-w-screen-xl mx-auto text-white text-lg font-semibold">
            <ul className="flex">
              <li>
                <img
                  className="h-20 p-2 mr-8"
                  src={require("../pictures/gerb.png")}
                  alt="Герб"
                />
              </li>
              <li className="hover:bg-sky-700 flex items-cetner px-2">
                <NavLink to="/reports" className="h-full flex items-center">
                  Шаблоны
                </NavLink>
              </li>
              <li className="hover:bg-sky-700 flex items-cetner px-2">
                <NavLink to="/users" className="h-full flex items-center">
                  Пользователи
                </NavLink>
              </li>
              {isAuthenticated ? (
                <>
                  <li className="bg-red-600 hover:bg-red-700 flex items-center px-2 ml-auto">
                    <button
                      onClick={() => {
                        logout();
                      }}
                      className="h-full flex items-center"
                    >
                      Выйти
                      <img
                        className="h-8 mt-1 ml-1 mr-4"
                        src={require("../pictures/logout.png")}
                        alt="Выйти"
                      />
                    </button>
                  </li>
                </>
              ) : (
                <>
                  {/* <li className="bg-green-600 md:hover:bg-green-700 flex items-center px-2 ml-auto">
                    <button onClick={openModal} className="h-full flex items-center focus:outline-none">
                      Войти
                      <img
                        className="h-8 mt-1 ml-1 mr-4 md:border-0"
                        src={require("../pictures/login.png")}
                        alt="Войти"
                      />
                    </button>
                  </li> */}
                </>
              )}
            </ul>
          </div>
        </nav>
  
        <ReactModal
          isOpen={isModalOpen}
          onRequestClose={closeModal}
          contentLabel="Авторизация"
          className="Modal"
          overlayClassName="Overlay"
        >
          <Login onLogin={onLogin} close = {closeModal} />
        </ReactModal>
      </>
    );
  };
  
