(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Type_taxpayerDetailController', Type_taxpayerDetailController);

    Type_taxpayerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Type_taxpayer'];

    function Type_taxpayerDetailController($scope, $rootScope, $stateParams, entity, Type_taxpayer) {
        var vm = this;
        vm.type_taxpayer = entity;
        vm.load = function (id) {
            Type_taxpayer.get({id: id}, function(result) {
                vm.type_taxpayer = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:type_taxpayerUpdate', function(event, result) {
            vm.type_taxpayer = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
