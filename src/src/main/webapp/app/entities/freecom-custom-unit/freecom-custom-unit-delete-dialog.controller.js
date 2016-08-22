(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_custom_unitDeleteController',Freecom_custom_unitDeleteController);

    Freecom_custom_unitDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_custom_unit'];

    function Freecom_custom_unitDeleteController($uibModalInstance, entity, Freecom_custom_unit) {
        var vm = this;

        vm.freecom_custom_unit = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Freecom_custom_unit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
