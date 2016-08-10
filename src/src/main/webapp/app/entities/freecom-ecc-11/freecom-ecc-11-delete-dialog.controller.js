(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ecc11DeleteController',Freecom_ecc11DeleteController);

    Freecom_ecc11DeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_ecc11'];

    function Freecom_ecc11DeleteController($uibModalInstance, entity, Freecom_ecc11) {
        var vm = this;

        vm.freecom_ecc11 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Freecom_ecc11.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
