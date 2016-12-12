(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_data_operationDeleteController',Freecom_data_operationDeleteController);

    Freecom_data_operationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_data_operation'];

    function Freecom_data_operationDeleteController($uibModalInstance, entity, Freecom_data_operation) {
        var vm = this;
        vm.freecom_data_operation = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_data_operation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
