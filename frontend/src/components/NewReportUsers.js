import React, { useEffect, useState } from "react";
import { request } from "../helpers/axios_helper";
import { toast } from "react-toastify";

export default function NewReportUsers({ report, closeModal }) {
  const [users, setUsers] = useState([]);
  const [selectedUsers, setSelectedUsers] = useState([]);

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      console.log(report);
      const response = await request("get", `/users/region/${report.region.id}`);
      setUsers(response.data);
    } catch (error) {
      toast.error("Не удалось получить список пользователей.");
    }
  };

  const handleUserSelection = (event, userId) => {
    if (event.target.checked) {
      setSelectedUsers([...selectedUsers, userId]);
    } else {
      setSelectedUsers(selectedUsers.filter((id) => id !== userId));
    }
  };

  const handleSelectAll = () => {
    const allUserIds = users.map((user) => user.id);
    setSelectedUsers(allUserIds);
  };

  const handleDeselectAll = () => {
    setSelectedUsers([]);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await request("post", "/reports", report);
      const reportId = response.data.id;
      for (const userId of selectedUsers) {
        const requestData = {
          reportId,
          userId,
        };
        await request("post", "/report/data", requestData);
      }
      toast.success("Отчет успешно создан.");
      closeModal();
    } catch (error) {
      toast.error("Не удалось добавить данные отчета.");
    }
  };

  return (
    <>
      <div className="mx-auto w-1/4 bg-white p-4 mt-60">
        <div className="flex justify-end">
          <button onClick={() => closeModal()}>
            <img
              className="h-6 md:border-0 hover:brightness-90"
              src={require("../pictures/close.png")}
              alt="Закрыть"
            />
          </button>
        </div>
        <h1 className="text-2xl font-bold mb-4">Выбор пользователей</h1>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
          <div className="flex mb-2">
          <button
              type="button"
              onClick={handleSelectAll}
              className="bg-blue-500 hover:bg-blue-600 w-1/2 mr-1 text-white font-bold py-2 px-4 rounded focus:outline-none"
            >
              Выбрать всех
            </button>
            <button
              type="button"
              onClick={handleDeselectAll}
              className="bg-red-500 hover:bg-red-600 w-1/2 ml-1 text-white font-bold py-2 px-4 rounded focus:outline-none"
            >
              Снять выбор
            </button>
          </div>
            <table className="w-full border-collapse">
              <thead>
                <tr>
                              
                  <th className="bg-gray-200 border-b border-gray-400 px-4 py-2 border-r">
                   
                  </th>
                    
                  <th className="bg-gray-200 border-b border-gray-400 px-4 py-2 text-left">
                    Пользователь
                  </th>
                </tr>
              </thead>
              <tbody className="max-h-48 overflow-y-auto">
                {users.map((user) => (
                  <tr key={user.id} className="border-b border-gray-400">
                    <td className="px-4 py-2 border-r">
                      <input
                        type="checkbox"
                        value={user.id}
                        checked={selectedUsers.includes(user.id)}
                        onChange={(event) =>
                          handleUserSelection(event, user.id)
                        }
                        className="form-checkbox"
                      />
                    </td>
                    <td className="px-4 py-2">{user.surname} {user.firstname} {user.patronymic}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          <button
            type="submit"
            className="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded focus:outline-none"
          >
            Создать отчет
          </button>
        </form>
      </div>
    </>
  );
}
