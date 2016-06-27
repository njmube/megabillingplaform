(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_digital_certificateDetailController', Free_digital_certificateDetailController);

    Free_digital_certificateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Free_digital_certificate'];

    function Free_digital_certificateDetailController($scope, $rootScope, $stateParams, entity, Free_digital_certificate) {
        var vm = this;
        vm.free_digital_certificate = entity;
        vm.load = function (id) {
            Free_digital_certificate.get({id: id}, function(result) {
                vm.free_digital_certificate = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:free_digital_certificateUpdate', function(event, result) {
            vm.free_digital_certificate = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
