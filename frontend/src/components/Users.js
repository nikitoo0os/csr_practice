import React, { useEffect, useState } from 'react';
import { request } from '../helpers/axios_helper';
import { NavLink } from 'react-router-dom';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ReactModal from "react-modal";
import NewUser from './NewUser';
import EditUser from './EditUser';

ReactModal.setAppElement("#root");

export default function Users() {
  const [users, setUsers] = useState([]);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [editUserId, setEditUserId] = useState(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);

  const openAddModal = () => {
    setIsAddModalOpen(true);
  };

  const closeAddModal = () => {
    setIsAddModalOpen(false);
  };

  const openEditModal = (userId) => {
    setEditUserId(userId);
    setIsEditModalOpen(true);
  };

  const closeEditModal = () => {
    setIsEditModalOpen(false);
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

  const handleDeleteUser = async (userId) => {
    try {
      // Отправляем запрос на удаление пользователя
      const response = await request('delete', `/users/${userId}`);

      // Проверяем успешный статус ответа
      if (response.status === 200) {
        // Обновляем список пользователей после удаления
        const updatedUsers = users.filter((user) => user.id !== userId);
        setUsers(updatedUsers);

        toast.success('Пользователь успешно удален');
      } else {
        // Обработка ошибки, если требуется
      }
    } catch (error) {
      // Обработка ошибки, если требуется
    }
  };

  return (
    <>
      <div className="max-w-screen-xl mx-auto bg-white">
        <button onClick={openAddModal} className="bg-green-500 md:hover:bg-green-600 font-bold px-3 py-2 m-2 rounded text-white mt-8 focus:outline-none">
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
                  <button onClick={() => handleDeleteUser(user.id)} className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded mr-2">Удалить</button>
                  <button onClick={() => openEditModal(user.id)} className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded">Изменить</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <ReactModal
        isOpen={isAddModalOpen}
        onRequestClose={closeAddModal}
        contentLabel="Новый пользователь"
        className="Modal"
        overlayClassName="Overlay"
      >
        <NewUser closeModal={closeAddModal}/>
      </ReactModal>
      <ReactModal
        isOpen={isEditModalOpen}
        onRequestClose={closeEditModal}
        contentLabel="Изменить данные пользователя"
        className="Modal"
        overlayClassName="Overlay"
      >
        {editUserId && <EditUser userId={editUserId} />}
      </ReactModal>
    </>
  );
}
