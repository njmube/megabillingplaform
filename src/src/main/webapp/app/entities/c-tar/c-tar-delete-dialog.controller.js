(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_tarDeleteController',C_tarDeleteController);

    C_tarDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_tar'];

    function C_tarDeleteController($uibModalInstance, entity, C_tar) {
        var vm = this;
        vm.c_tar = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_tar.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
