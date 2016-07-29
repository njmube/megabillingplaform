(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_transit_typeDeleteController',C_transit_typeDeleteController);

    C_transit_typeDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_transit_type'];

    function C_transit_typeDeleteController($uibModalInstance, entity, C_transit_type) {
        var vm = this;
        vm.c_transit_type = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_transit_type.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
