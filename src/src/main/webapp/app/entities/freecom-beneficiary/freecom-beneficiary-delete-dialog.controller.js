(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_beneficiaryDeleteController',Freecom_beneficiaryDeleteController);

    Freecom_beneficiaryDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_beneficiary'];

    function Freecom_beneficiaryDeleteController($uibModalInstance, entity, Freecom_beneficiary) {
        var vm = this;

        vm.freecom_beneficiary = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Freecom_beneficiary.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
