import React, { useState } from "react";
import { useLocation } from 'react-router-dom';
import { request } from '../helpers/axios_helper';
import { toast } from 'react-toastify';
import ExcelJS from "exceljs";

export default function SummaryRepRead() {
    const location = useLocation();
    const tableData = location.state && location.state.tableData;
    const xlsxData = location.state && location.state.tableData;

    if (!tableData || tableData.length === 2) {
        return <div className="bg-white p-4 max-w-screen-xl mx-auto">Невозможно сформировать итоговый отчет, т.к. нету завершенных отчетов по заданным параметрам.</div>;
    }

    const headers = Object.keys(tableData[0]); // Ключи первого объекта - заголовки
    const totals = Object.keys(tableData[tableData.length - 1]); // Ключи последнего объекта - итоговые значения
    const dataRows = tableData.slice(1, -1); // Остальные элементы - данные
    const downloadXLSX = async () => {
        
        const workbook = new ExcelJS.Workbook();
        const worksheet = workbook.addWorksheet("Отчет");

        const columnWidths = [10, 50, 25, 25, 25, 25];
        worksheet.columns = columnWidths.map((width) => ({ width }));
        worksheet.autoRowHeight = true;

        xlsxData.forEach((item) => {
            worksheet.addRow(Object.values(item));
        });

        worksheet.eachRow((row, rowNumber) => {
            row.alignment = {
                vertical: "middle",
                wrapText: true,
            };
        });

        worksheet.eachRow((row, rowNumber) => {
            row.eachCell((cell, colNumber) => {
                cell.border = {
                    top: { style: "thin" },
                    left: { style: "thin" },
                    bottom: { style: "thin" },
                    right: { style: "thin" },
                };
            });
        });

        const firstRow = worksheet.getRow(1);
        firstRow.eachCell((cell, colNumber) => {
            if (colNumber <= 6) {
                cell.font = { bold: true };
                cell.fill = {
                    type: "pattern",
                    pattern: "solid",
                    fgColor: { argb: "fff2cc" },
                };
                cell.alignment = { horizontal: "center", vertical: "middle" };
            }
        });

        worksheet.eachRow((row, rowIndex) => {
            if (rowIndex >= 2) {
                row.eachCell((cell, colNumber) => {
                    if (colNumber >= 3) {
                        cell.alignment = { horizontal: "center", vertical: "middle" };
                    }
                });
            }
        });
        worksheet.getColumn(1).alignment = {
            horizontal: "center",
            vertical: "middle",
        };
        const lastRow = worksheet.getRow(xlsxData.length);

        lastRow.eachCell((cell, colNumber) => {
            if (colNumber === 2) {
                cell.font = { bold: true, italic: true };
                cell.alignment = { vertical: "middle", horizontal: "right" };
            }
        });
        const buffer = await workbook.xlsx.writeBuffer();
        const blob = new Blob([buffer], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement("a");
        a.href = url;
        a.download = "Итоговый отчет.xlsx";
        a.click();
        window.URL.revokeObjectURL(url);
    };
    return (
        <div className="bg-white p-4">
            <button
                className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded mb-4 w-1/4 focus:outline-none outline-none"
                onClick={downloadXLSX}
            >
                Скачать XLSX файл
            </button>
            <table className="w-full border-collapse border border-gray-300">
                <thead>
                    <tr className="bg-gray-200">
                        {headers.map((header, index) => (
                            <th
                                key={index}
                                className={`w-${index === 0 ? '1' : index === 1 ? '5' : '2'}/12 p-2 border border-gray-300 text-gray-800`}
                            >
                                {tableData[0][header]}
                            </th>
                        ))}
                    </tr>
                </thead>
                <tbody>
                    {dataRows.map((rowData, rowIndex) => (
                        <tr key={rowIndex}>
                            {headers.map((header, index) => (
                                <td
                                    key={index}
                                    className={`w-${index === 0 ? '1' : index === 1 ? '5' : '2'}/12 p-2 border border-gray-300 text-${index === 1 ? 'left' : 'center'}`}
                                >
                                    {rowData[header]}
                                </td>
                            ))}
                        </tr>
                    ))}
                </tbody>
                <tfoot>
                    <tr className="bg-gray-200">
                        {totals.map((total, index) => (
                            <td
                                key={index}
                                className={`w-${index === 0 ? '1' : index === 1 ? '5' : '2'}/12 p-2 border border-gray-300 text-${index === 1 ? 'right' : 'center'} font-semibold italic text-gray-700`}
                            >
                                {tableData[tableData.length - 1][total]}
                            </td>
                        ))}
                    </tr>
                </tfoot>
            </table>
        </div>
    );
}
