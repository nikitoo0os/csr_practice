import React from "react";
import { NavLink } from "react-router-dom";
import { setAuthHeader } from "../helpers/axios_helper";
import "react-toastify/dist/ReactToastify.css";
import ReactModal from "react-modal";


ReactModal.setAppElement("#root");

function logout() {
    setAuthHeader(null);
    window.location.reload();
    window.location.assign('/')
};

export const Navbar = ({ isAuthenticated, isAdmin }) => {
  
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
              {isAdmin ? (<>
                <li className="hover:bg-sky-700 flex items-cetner px-2">
                <NavLink to="/" className="h-full flex items-center">
                  Шаблоны и отчеты
                </NavLink>
              </li>
              <li className="hover:bg-sky-700 flex items-cetner px-2">
                <NavLink to="/users" className="h-full flex items-center">
                  Пользователи
                </NavLink>
              </li>
              </>):(<> {isAuthenticated && (  <li className="hover:bg-sky-700 flex items-cetner px-2">
                <NavLink to="/" className="h-full flex items-center">
                  Мои отчеты
                </NavLink>
              </li>)}
              
              </>)}
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
      </>
    );
  };
  
