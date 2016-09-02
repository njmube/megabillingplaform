(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Branch_officeDeleteController',Branch_officeDeleteController);

    Branch_officeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Branch_office'];

    function Branch_officeDeleteController($uibModalInstance, entity, Branch_office) {
        var vm = this;

        vm.branch_office = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Branch_office.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
