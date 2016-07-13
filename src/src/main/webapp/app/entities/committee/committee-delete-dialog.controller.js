(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('CommitteeDeleteController',CommitteeDeleteController);

    CommitteeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Committee'];

    function CommitteeDeleteController($uibModalInstance, entity, Committee) {
        var vm = this;
        vm.committee = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Committee.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
