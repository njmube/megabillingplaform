(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_school_levelDeleteController',C_school_levelDeleteController);

    C_school_levelDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_school_level'];

    function C_school_levelDeleteController($uibModalInstance, entity, C_school_level) {
        var vm = this;
        vm.c_school_level = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_school_level.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
