(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_complementDeleteController',C_complementDeleteController);

    C_complementDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_complement'];

    function C_complementDeleteController($uibModalInstance, entity, C_complement) {
        var vm = this;

        vm.c_complement = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            C_complement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
