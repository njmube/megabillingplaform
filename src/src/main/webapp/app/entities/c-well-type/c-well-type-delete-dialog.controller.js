(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_well_typeDeleteController',C_well_typeDeleteController);

    C_well_typeDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_well_type'];

    function C_well_typeDeleteController($uibModalInstance, entity, C_well_type) {
        var vm = this;
        vm.c_well_type = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_well_type.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
