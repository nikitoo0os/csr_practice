import React, { useEffect, useState } from 'react';
import { request } from '../helpers/axios_helper';
import { NavLink } from 'react-router-dom';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ReactModal from "react-modal";
import NewUser from './NewUser';

ReactModal.setAppElement("#root");


export default function Users() {
    const [users, setUsers] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
  
    const openModal = () => {
      setIsModalOpen(true);
    };
  
    const closeModal = () => {
      setIsModalOpen(false);
    };

    useEffect(() => {
        const fetchUsers = async () => {
          try {
            const response = await request('get', '/users');
            if (response.status === 200) {
              setUsers(response.data);
            } else {
              // Обработка ошибки, если требуется
            }
          } catch (error) {
            // Обработка ошибки, если требуется
            toast.error('Ошибка при получении данных пользователей');
          }
        };
    
        fetchUsers();
      }, []);
      
    return (
        <>
            <div className="max-w-screen-xl mx-auto bg-white">
                <button onClick={openModal} className="bg-green-500 md:hover:bg-green-600 font-bold px-3 py-2 m-2 rounded text-white mt-8 focus:outline-none">
                    Добавить
                </button>
                <table className="table-auto w-full">
                    <thead>
                        <tr className="bg-sky-600 text-white">
                            <th className="px-4 py-2 w-1/4 border">ФИО</th>
                            <th className="px-4 py-2 w-1/4 border">Почта</th>
                            <th className="px-4 py-2 w-1/4 border">Регион</th>
                            <th className="px-4 py-2 w-1/4 border">Действия</th>
                        </tr>
                    </thead>
                    <tbody>
                        {users.map((user) => (
                            <tr key={user.id}>
                                <td className="border px-4 py-2">{user.fullName}</td>
                                <td className="border px-4 py-2">{user.email}</td>
                                <td className="border px-4 py-2">{user.region}</td>
                                <td className="border px-4 py-2">
                                    <button className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded mr-2">Удалить</button>
                                    <button className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded">Изменить</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <ReactModal
          isOpen={isModalOpen}
          onRequestClose={closeModal}
          contentLabel="Новый пользователь"
          className="Modal"
          overlayClassName="Overlay"
        >
          <NewUser/>
        </ReactModal>
        </>
    );
}