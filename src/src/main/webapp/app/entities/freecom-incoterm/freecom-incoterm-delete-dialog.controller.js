(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_incotermDeleteController',Freecom_incotermDeleteController);

    Freecom_incotermDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_incoterm'];

    function Freecom_incotermDeleteController($uibModalInstance, entity, Freecom_incoterm) {
        var vm = this;

        vm.freecom_incoterm = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Freecom_incoterm.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
