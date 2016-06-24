(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Rate_typeDetailController', Rate_typeDetailController);

    Rate_typeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Rate_type'];

    function Rate_typeDetailController($scope, $rootScope, $stateParams, entity, Rate_type) {
        var vm = this;
        vm.rate_type = entity;
        vm.load = function (id) {
            Rate_type.get({id: id}, function(result) {
                vm.rate_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:rate_typeUpdate', function(event, result) {
            vm.rate_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
