(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_type_roadDeleteController',C_type_roadDeleteController);

    C_type_roadDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_type_road'];

    function C_type_roadDeleteController($uibModalInstance, entity, C_type_road) {
        var vm = this;
        vm.c_type_road = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_type_road.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
