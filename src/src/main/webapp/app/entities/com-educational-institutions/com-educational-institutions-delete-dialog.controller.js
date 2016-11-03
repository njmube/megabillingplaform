(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_educational_institutionsDeleteController',Com_educational_institutionsDeleteController);

    Com_educational_institutionsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_educational_institutions'];

    function Com_educational_institutionsDeleteController($uibModalInstance, entity, Com_educational_institutions) {
        var vm = this;
        vm.com_educational_institutions = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_educational_institutions.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
