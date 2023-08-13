import React, { useEffect, useState } from "react";
import { request } from "../helpers/axios_helper";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import ReactModal from "react-modal";
import NewUser from "./NewUser";
import EditUser from "./EditUser";
import UserReports from "./UserReports";

ReactModal.setAppElement("#root");

export default function Users() {
  const [users, setUsers] = useState([]);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [editUserId, setEditUserId] = useState(null);
  const [reportsUserId, setReportsUserId] = useState(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isUserAdded, setIsUserAdded] = useState(false);
  const [filterValue, setFilterValue] = useState("");
  const [selectedRegion, setSelectedRegion] = useState("");
  const [regions, setRegions] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(10);
  const [isReportsModalOpen, setIsReportsModalOpen] = useState(false);
  const [userToDelete, setUserToDelete] = useState(null);



  const openReportsModal = (userId) => {
    setReportsUserId(userId);
    setIsReportsModalOpen(true);
  };

  const closeReportsModal = () => {
    setIsReportsModalOpen(false);
  };

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
      const response = await request("get", "/users");
      if (response.status === 200) {
        setUsers(response.data);
      } else {
        // Обработка ошибки, если требуется
      }
    } catch (error) {
      // Обработка ошибки, если требуется
      toast.error("Ошибка при получении данных пользователей");
    }
  };

  const fetchRegions = async () => {
    try {
      const response = await request("get", "/regions");
      if (response.status === 200) {
        setRegions(response.data);
      } else {
        // Обработка ошибки, если требуется
      }
    } catch (error) {
      // Обработка ошибки, если требуется
      toast.error("Ошибка при получении данных регионов");
    }
  };

  useEffect(() => {
    fetchUsers();
    fetchRegions();
  }, [isUserAdded]);

  const handleSoftDeleteUser = (user) => {
    setUserToDelete(user);
  };


  const handleFilterChange = (event) => {
    setCurrentPage(1);
    setFilterValue(event.target.value);
  };

  const filteredUsers = users.filter((user) => {
    const surnameMatch = user.surname
      .toLowerCase()
      .includes(filterValue.toLowerCase());
    const regionMatch =
      !selectedRegion || user.region.id === parseInt(selectedRegion, 10);
    return surnameMatch && regionMatch;
  });

  const getTotalPages = () => Math.max(Math.ceil(filteredUsers.length / pageSize), 1);

  useEffect(() => {
    // Ensure current page does not exceed total pages when data changes
    if (currentPage > getTotalPages()) {
      setCurrentPage(getTotalPages());
    }
  }, [filteredUsers, currentPage]);

  const indexOfLastUser = currentPage * pageSize;
  const indexOfFirstUser = indexOfLastUser - pageSize;
  const currentUsers = filteredUsers.slice(indexOfFirstUser, indexOfLastUser);

  const handleSoftDeleteUserConfirmed = async (user) => {
    try {
      const response = await request("delete", `/users/${user.id}`);
      if (response.status === 200) {
        fetchUsers();
        toast.success("Пользователь успешно удален");
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
        <button
          onClick={openAddModal}
          className="bg-green-500 md:hover:bg-green-600 font-bold px-3 py-2 m-2   rounded text-white focus:outline-none shadow-lg mt-8"
        >
          Добавить нового пользователя
        </button>
        <div className="border-2 w-1/2 p-2 mb-2 ml-2 mt-4 shadow-lg">
          <h2 className="font-semibold border-b-2">Фильтр</h2>
          <label>Регион</label>
          <select
            value={selectedRegion}
            onChange={(e) => setSelectedRegion(e.target.value)}
            className="border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2 mt-4 mb-2 m-2"
          >
            <option value="">Все регионы</option>
            {regions.map((region) => (
              <option key={region.id} value={region.id}>
                {region.name}
              </option>
            ))}
          </select>
          <label className="ml-2">Фамилия</label>
          <input
            type="text"
            value={filterValue}
            onChange={handleFilterChange}
            className="border border-gray-300 focus:outline-none focus:border-sky-500 rounded-md px-4 py-2 mb-2 m-2"
            placeholder="Фильтр по фамилии"
          />
        </div>
        <table className="table-auto w-full">
          <thead>
            <tr className="bg-sky-600 text-white">
              <th className="px-4 py-2 w-3/12 border">ФИО</th>
              <th className="px-4 py-2 w-3/12 border">Почта</th>
              <th className="px-4 py-2 w-2/12 border">Регион</th>
              <th className="px-4 py-2 w-4/12 border">Действия</th>
            </tr>
          </thead>
          <tbody>
            {currentUsers.map((user) => (
              <tr key={user.id}>
                <td className="border px-4 py-2">
                  {user.surname} {user.firstname[0]}. {user.patronymic[0]}.
                </td>
                <td className="border px-4 py-2">{user.email}</td>
                <td className="border px-4 py-2">{user.region.name}</td>
                <td className="border px-4 py-2">
                  <button
                    onClick={() => handleSoftDeleteUser(user)}
                    className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded mr-2 shadow-md focus:outline-none focus:border-0"
                  >
                    Удалить
                  </button>

                  <button
                    onClick={() => openEditModal(user.id)}
                    className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded mr-2 focus:outline-none focus:border-0 shadow-md"
                  >
                    Изменить
                  </button>
                  <button
                    onClick={() => openReportsModal(user.id)}
                    className="bg-orange-500 hover:bg-orange-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:border-0 shadow-md"
                  >
                    Отчеты
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <div className="flex items-center mt-4 px-4 py-2">
          <button
            disabled={currentPage === 1}
            onClick={() => setCurrentPage(currentPage - 1)}
            className={`px-4 py-2 rounded-lg border ${currentPage === 1 ? "bg-gray-200" : "bg-sky-200 text-blue-600"
              } mr-2`}
          >
            ❮
          </button>
          <span className="font-semibold text-blue-600">
            {currentPage}/{getTotalPages()}
          </span>
          <button
            disabled={currentPage >= getTotalPages()}
            onClick={() => setCurrentPage(currentPage + 1)}
            className={`px-4 py-2 rounded-lg border ${currentPage >= getTotalPages() ? "bg-gray-200" : "bg-sky-200 text-blue-600"
              } ml-2`}
          >
            ❯
          </button>
        </div>
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
        <EditUser
          userId={editUserId}
          fetchUsers={fetchUsers}
          closeModal={closeEditModal}
        />
      </ReactModal>
      <ReactModal
        isOpen={isReportsModalOpen}
        onRequestClose={closeReportsModal}
        contentLabel="Отчеты пользователя"
        className="Modal"
        overlayClassName="Overlay"
      >
        <UserReports
          userId={reportsUserId}
          closeModal={closeReportsModal}
        />
      </ReactModal>
      <ReactModal
        isOpen={userToDelete !== null}
        onRequestClose={() => setUserToDelete(null)}
        contentLabel="Подтвердите удаление"
        className="Modal"
        overlayClassName="Overlay"
      >
        <div className="text-center bg-white w-1/3 mx-auto p-4 rounded">
          <p>Вы действительно хотите удалить пользователя {userToDelete?.surname} {userToDelete?.firstname} {userToDelete?.patronymic}?</p>
          <div className="mt-4">
            <button
              onClick={() => {
                handleSoftDeleteUserConfirmed(userToDelete);
                setUserToDelete(null);
              }}
              className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded mr-2 shadow-md"
            >
              Подтвердить
            </button>
            <button
              onClick={() => setUserToDelete(null)}
              className="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded shadow-md"
            >
              Отмена
            </button>
          </div>
        </div>
      </ReactModal>

    </>
  );
}