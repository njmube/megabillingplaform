(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_addresseeDeleteController',Com_addresseeDeleteController);

    Com_addresseeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_addressee'];

    function Com_addresseeDeleteController($uibModalInstance, entity, Com_addressee) {
        var vm = this;
        vm.com_addressee = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_addressee.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
