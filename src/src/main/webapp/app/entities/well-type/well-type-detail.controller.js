(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Well_typeDetailController', Well_typeDetailController);

    Well_typeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Well_type'];

    function Well_typeDetailController($scope, $rootScope, $stateParams, entity, Well_type) {
        var vm = this;
        vm.well_type = entity;
        vm.load = function (id) {
            Well_type.get({id: id}, function(result) {
                vm.well_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:well_typeUpdate', function(event, result) {
            vm.well_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
