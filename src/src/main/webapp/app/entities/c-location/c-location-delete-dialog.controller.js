(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_locationDeleteController',C_locationDeleteController);

    C_locationDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_location'];

    function C_locationDeleteController($uibModalInstance, entity, C_location) {
        var vm = this;
        vm.c_location = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_location.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
