(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_locationDetailController', C_locationDetailController);

    C_locationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_location', 'C_municipality', 'C_zip_code'];

    function C_locationDetailController($scope, $rootScope, $stateParams, entity, C_location, C_municipality, C_zip_code) {
        var vm = this;
        vm.c_location = entity;
        vm.load = function (id) {
            C_location.get({id: id}, function(result) {
                vm.c_location = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_locationUpdate', function(event, result) {
            vm.c_location = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
