(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ecc11_conceptDeleteController',Freecom_ecc11_conceptDeleteController);

    Freecom_ecc11_conceptDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_ecc11_concept'];

    function Freecom_ecc11_conceptDeleteController($uibModalInstance, entity, Freecom_ecc11_concept) {
        var vm = this;

        vm.freecom_ecc11_concept = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Freecom_ecc11_concept.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
