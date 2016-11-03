(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_doneesDeleteController',Com_doneesDeleteController);

    Com_doneesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_donees'];

    function Com_doneesDeleteController($uibModalInstance, entity, Com_donees) {
        var vm = this;
        vm.com_donees = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_donees.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
