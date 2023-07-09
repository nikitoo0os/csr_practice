import React, { useEffect, useState } from 'react';
import { request } from '../helpers/axios_helper';
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
  const [isUserAdded, setIsUserAdded] = useState(false);

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

  useEffect(() => {
    if (isUserAdded) {
      fetchUsers(); // Обновляем список пользователей
      setIsUserAdded(false); // Сбрасываем флаг добавления пользователя
    }
    fetchUsers();
  }, [isUserAdded]);

  const handleSoftDeleteUser = async (userId) => {
    try {
      // Отправляем запрос на мягкое удаление пользователя
      const response = await request('delete', `/users/${userId}`);
  
      // Проверяем успешный статус ответа
      if (response.status === 200) {
        // Обновляем список пользователей после удаления
        fetchUsers(); // Вызываем метод fetchUsers для обновления списка
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
      <div className="max-w-screen-xl mx-auto bg-white shadow-md">
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
                  {!user.isDeleted && (
                    <>
                      <button onClick={() => handleSoftDeleteUser(user.id)} className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded mr-2">Удалить</button>
                      <button onClick={() => openEditModal(user.id)} className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded">Изменить</button>
                    </>
                  )}
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
        <NewUser closeModal={closeAddModal} setIsUserAdded={setIsUserAdded} />
      </ReactModal>
      <ReactModal
        isOpen={isEditModalOpen}
        onRequestClose={closeEditModal}
        contentLabel="Изменить данные пользователя"
        className="Modal"
        overlayClassName="Overlay"
      >
        <EditUser userId={editUserId} />
      </ReactModal>
    </>
  );
}
