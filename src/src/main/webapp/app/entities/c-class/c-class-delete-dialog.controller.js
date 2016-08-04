(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_classDeleteController',C_classDeleteController);

    C_classDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_class'];

    function C_classDeleteController($uibModalInstance, entity, C_class) {
        var vm = this;
        vm.c_class = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_class.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
