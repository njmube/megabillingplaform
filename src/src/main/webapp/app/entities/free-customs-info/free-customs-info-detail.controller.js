(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_customs_infoDetailController', Free_customs_infoDetailController);

    Free_customs_infoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Free_customs_info', 'Free_concept', 'Free_part_concept'];

    function Free_customs_infoDetailController($scope, $rootScope, $stateParams, entity, Free_customs_info, Free_concept, Free_part_concept) {
        var vm = this;
        vm.free_customs_info = entity;
        vm.load = function (id) {
            Free_customs_info.get({id: id}, function(result) {
                vm.free_customs_info = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:free_customs_infoUpdate', function(event, result) {
            vm.free_customs_info = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
