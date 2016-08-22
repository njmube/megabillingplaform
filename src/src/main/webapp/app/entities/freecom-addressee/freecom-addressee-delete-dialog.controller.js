(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_addresseeDeleteController',Freecom_addresseeDeleteController);

    Freecom_addresseeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_addressee'];

    function Freecom_addresseeDeleteController($uibModalInstance, entity, Freecom_addressee) {
        var vm = this;

        vm.freecom_addressee = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Freecom_addressee.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
