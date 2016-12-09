(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_data_operationDeleteController',Com_data_operationDeleteController);

    Com_data_operationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_data_operation'];

    function Com_data_operationDeleteController($uibModalInstance, entity, Com_data_operation) {
        var vm = this;
        vm.com_data_operation = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_data_operation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
