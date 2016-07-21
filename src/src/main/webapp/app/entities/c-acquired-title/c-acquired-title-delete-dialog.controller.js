(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_acquired_titleDeleteController',C_acquired_titleDeleteController);

    C_acquired_titleDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_acquired_title'];

    function C_acquired_titleDeleteController($uibModalInstance, entity, C_acquired_title) {
        var vm = this;
        vm.c_acquired_title = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_acquired_title.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
